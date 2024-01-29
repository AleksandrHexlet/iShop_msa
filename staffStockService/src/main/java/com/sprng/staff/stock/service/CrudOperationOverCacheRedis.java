package com.sprng.staff.stock.service;

import com.sprng.library.exception.IshopResponseException;
import com.sprng.staff.stock.model.StaffStock;
import com.sprng.staff.stock.repository.StaffStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CrudOperationOverCacheRedis {
    private KafkaTemplate<Integer, StaffStock> kafkaTemplate;
    private StaffStockRepository staffStockRepository;
    private RedisTemplate<Integer, StaffStock> redisTemplate;

    @Autowired
    public CrudOperationOverCacheRedis(KafkaTemplate<Integer, StaffStock> kafkaTemplate,
                                       StaffStockRepository staffStockRepository, RedisTemplate<Integer, StaffStock> redisTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.staffStockRepository = staffStockRepository;
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public void saveStackStock() {
        StaffStock staffStockInit = new StaffStock("pencil", 50);
        staffStockRepository.save(staffStockInit);
    }


    @KafkaListener(topicPartitions = @TopicPartition(topic = "createOrder", partitions = "0"))
    public void getResponse(@Payload StaffStock staffStockProductOrder, @Header(KafkaHeaders.RECEIVED_KEY) int key) {
//        StaffStock staffOrder = new StaffStock(staffStockProductOrder.getNamePosition(), 0);
        try {
            if (redisTemplate.hasKey(staffStockProductOrder.getId())) { // проверяем, что обьект есть в кеше Редиса
                // перед удалением также проверяем наличие обьекта в памяти
                Mono.just(redisTemplate.opsForValue().get(staffStockProductOrder.getId()))
                        .doOnNext(staffStockFromDB -> {
                            staffStockFromDB.setCount(staffStockFromDB.getCount() - staffStockProductOrder.getCount());
                            staffStockFromDB.setSuccess(true);
                            if (staffStockProductOrder.getCount() > staffStockFromDB.getCount()) {
                                staffStockProductOrder.setNeededOrder(staffStockProductOrder.getCount() - staffStockFromDB.getCount()); // сколько необходимо дозаказать для выполнения заказа
                            } else {
                                staffStockProductOrder.setNeededOrder(0);
                            }
                            staffStockRepository.save(staffStockFromDB);
                        });
            } else {
                staffStockRepository.findById(staffStockProductOrder.getId()).doOnNext(staffStockFromDB -> {

                    staffStockFromDB.setCount(staffStockFromDB.getCount() - staffStockProductOrder.getCount());
                    staffStockFromDB.setSuccess(true);
                    if (staffStockProductOrder.getCount() > staffStockFromDB.getCount()) {
                        staffStockProductOrder.setNeededOrder(staffStockProductOrder.getCount() - staffStockFromDB.getCount()); // сколько необходимо дозаказать для выполнения заказа
                    } else {
                        staffStockProductOrder.setNeededOrder(0);
                    }
                    staffStockRepository.save(staffStockFromDB);
                });
            }

            redisTemplate.opsForValue().set(staffStockProductOrder.getId(), staffStockProductOrder);//другой способ сохранить кеш в Redis
            boolean isExistKey = redisTemplate.hasKey(staffStockProductOrder.getId());//другой способ проверить содержится ли обьект в кеше Redis
            redisTemplate.opsForValue().get(staffStockProductOrder.getId());//другой способ получить кеш из Redis
            redisTemplate.delete(staffStockProductOrder.getId());//другой способ удалить кеш из Redis
//            CRUDoperationOverCacheForRedis
            kafkaSend("staffStockSuccess", 0, staffStockProductOrder.getId(), staffStockProductOrder);
        } catch (Exception exception) {
            staffStockProductOrder.setSuccess(false);
            kafkaSend("staffStockFail", 0, staffStockProductOrder.getId(), staffStockProductOrder);
        }
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "notificationFail", partitions = "0"))
    // для снятия резерва
    public void rollBackDB(@Payload StaffStock staffStock, @Header(KafkaHeaders.RECEIVED_KEY) int id) {
        staffStockRepository.findByNamePosition(staffStock.getNamePosition())
                .doOnNext(staffStockFromDB -> {
                    staffStockFromDB.setCount(staffStockFromDB.getCount() + staffStock.getCount());
                    staffStockRepository.save(staffStockFromDB); // rollBack DB
                });
    }

    private void kafkaSend(String topicName, int partitionNumber, int id, StaffStock staffOrder) {
        kafkaTemplate.send(topicName, partitionNumber, id, staffOrder)
                .whenComplete((result, throwable) -> {
                    System.out.println("result.getProducerRecord().key(); = " + result.getProducerRecord().key());
                    System.out.println("result.topic.name = " + result.getRecordMetadata().topic());
                    if (throwable != null) try {
                        throw new IshopResponseException("staffOrder not write");
                    } catch (IshopResponseException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @CachePut(key = "#staffStock.getId()", value = "StaffStock") // сохраняем данные в cache в БД Redis (просто память
    // на нашем же сервере без обращения в стороннюю БД по сети)
    public void setDataToCacheRedis(StaffStock staffStock) {
        staffStockRepository.findByNamePosition(staffStock.getNamePosition())
                .doOnNext(staffStockFromDB -> {
                    staffStockFromDB.setCount(staffStockFromDB.getCount() + staffStock.getCount());
                    staffStockRepository.save(staffStockFromDB); // rollBack DB
                });
    }

    @Cacheable(key = "#staffStock.getId()", value = "StaffStock") // получаем данные из cache в БД Redis(это просто
    // память на нашем же сервере и быстро получать данные не гоняя запросы по сети),
    // вместо обращения в реальную БД postgrees. Если в cache данных не будет , тогда запрос пойдет в реальную БД postgrees
    public void getDataToCacheRedis(StaffStock staffStock) {
        staffStockRepository.findById(staffStock.getId()).doOnNext(staffStockFromDB -> {
            System.out.println(staffStockFromDB.getCount());
        });
    }


    @CacheEvict(key = "#staffStock.getId()", value = "StaffStock") // получаем данные из cache в БД Redis(это просто
    // память на нашем же сервере и быстро получать данные не гоняя запросы по сети),
    // вместо обращения в реальную БД postgrees. Если в cache данных не будет , тогда запрос пойдет в реальную БД postgrees
    public void deleteDataToCacheRedis(StaffStock staffStock) {
        staffStockRepository.deleteById(staffStock.getId());
    }
}


//проверяем, что количество товара на складе достаточное.

//
// Если товара на складе не достаточно,
// тогда списываем сколько есть и // тогда отправляем уведомление о необходимости до заказа
// (надо 5 штук , а есть 3 штуки. Три штуки отправляем // заказчику и после пополнения отправим
// еще две штуки)  и отправим уведомление о необходимости   // пополнения склада.

// сохрани только карандаши 50 штук и потом отнимай из БД , количество корандашей из заказа пришедшего из
// хендлера  @Payload StaffStock staffStock и отправляй событие "выполнение заказа" в кафку
// И в нотификейшен слушай событие "выполнение заказа" из кафки, создай модель и записывай в БД само сообщение и время прихода сообщения LocalDate.now
