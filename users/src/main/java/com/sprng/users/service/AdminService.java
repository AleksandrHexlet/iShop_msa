package com.sprng.users.service;

import com.sprng.library.entity.AdminIshop;
import com.sprng.library.entity.Role;
import com.sprng.library.exception.IshopResponseException;
import com.sprng.users.repository.AdminRepository;
import com.sprng.users.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class AdminService {

    private RoleRepository roleRepository;
    private AdminRepository adminRepository;

    @Autowired
    public AdminService(
            RoleRepository roleRepository, AdminRepository adminRepository) {

        this.roleRepository = roleRepository;
        this.adminRepository = adminRepository;
    }

    public AdminIshop getAdminByUserName(String username) throws IshopResponseException{
        return adminRepository.findByUserName(username)
                .orElseThrow(()-> new IshopResponseException( "Admin not found"));

    }


    @Bean
    public CommandLineRunner createTableAdminIshop() {
        return (args) -> {

            Role roleAdmin = new Role();
            roleAdmin.setRoleType(Role.RoleType.ROLE_ADMIN);

            Role roleReadOnlyAdmin = new Role();
            roleReadOnlyAdmin.setRoleType(Role.RoleType.ROLE_READONLY_ADMIN);

            roleRepository.save(roleAdmin);
            roleRepository.save(roleReadOnlyAdmin);

            AdminIshop admin = new AdminIshop("Иван Админ");
            admin.setRole(roleAdmin);
            admin.setUserName("admin");
            admin.setPassword("$2a$10$7oCTGflP2kNI3WP41FV2IOFyXVh9beW6e9ywgsew3/rmIOxoEq/LW");

            AdminIshop readOnlyAdmin = new AdminIshop("Ivan ReadOnly");
            readOnlyAdmin.setRole(roleReadOnlyAdmin);
            readOnlyAdmin.setUserName("readOnlyAdmin");
            readOnlyAdmin.setPassword("$2a$10$fnDj5PUIC0rWC1otWLxHbeRXK8Plfh1oHGriPC6QBI5cP99Tb3wTq");
//
            adminRepository.save(admin);
            adminRepository.save(readOnlyAdmin);

        };
    }
//    public Product createNewProduct(@Valid Product product) throws ResponseException {
//        product.setDateAdded(LocalDate.now());
//        ProductTrader productTrader = productTraderRepository
//                .findByUserName(product.getProductManufacturer().getUserName()).orElse(null);
//        if (productTrader == null || !productTrader.isActive()) {
//            throw new ResponseException("Товар не может быть добавлен. Производитель " +
//                    "не существует ил не активен");
//        }
//        Product prod = productRepositoryAdmin.save(product);
//
//        return prod;
//    }
//
//    public Customer createNewCustomer(@Valid Customer customer) throws ResponseException {
//        Customer customerIsExist = customerRepositoryAdmin
//                .getCustomersByUserName(customer.getUserName()).get();
//        if (customerIsExist != null) {
//            throw new ResponseException("Клиент с таким userName уже существует ");
//        }
//        return customerRepositoryAdmin.save(customer);
//
//
//    }
//
//    public Category createNewCategory(@Valid Category category) throws ResponseException {
//        Category сategoryIsExist = categoryRepository.findByName(category.getName());
//        if (сategoryIsExist != null) {
//            throw new ResponseException("Категория с таким именем уже существует ");
//        }
//        return categoryRepository.save(category);
//    }
//
//
//    public ProductTrader createNewTrader(@Valid ProductTrader productTrader) {
//        productTrader.setDateRegistration(LocalDate.now());
//        productTraderRepository.save(productTrader);
//        return productTrader;
//    }
//
//    public void updateTrader(@Size(min = 1, max = 99,message = "Нарушены требование к длине имени") String userName,
//                             String  rate, String cityStorage) throws ResponseException {
//        if (userName == null)
//            throw new ResponseException("userName должен быть заполнен");
//        if ((cityStorage != null && cityStorage.isEmpty() && rate.equals("-1")) || (cityStorage == null && rate.equals("-1")))
//            throw new ResponseException("Нужна информация для обновления. Сейчас данные не переданы");
//       ProductTrader productTrader =  productTraderRepository.findByUserName(userName).orElse(null);
//       if(productTrader == null) throw new ResponseException("Продавец не зарегистрирован");
//        if(productTrader.getCityStorage().equals(cityStorage) && productTrader.getRate() == rate)
//            throw new ResponseException("Данные не требуют обновления.Они идентичны сохранённым в БД");
//        if(!rate.equals("-1")) productTrader.setRate(rate);
//        if(cityStorage != null) productTrader.setCityStorage(cityStorage);
//        productTraderRepository.save(productTrader);
//    }
//
//    public  List<HistoryOrder>  getOrderTrader(String username, String typeFilter
////                                                  @FutureOrPresent @PastOrPresent  LocalDate date
////                                                  @Min(0) @Max(99999) @Positive @Negative int something
////                                                  List<@Size(min = 0, max = 56789) String> strList
////                                                  List<@Min(0) @Max(12356789) @PositiveOrZero Integer> intList
////                                                  List<@Valid Product> productList
//    ) {
//        List<HistoryOrder> historyOrderList = new ArrayList<>();
//        List<ProductCustomerOrder> productCustomerOrderList = productCustomerOrderRepository.findByProductTraderUserName(username);
//        List<Product> productList = null;
//        Map<CustomerOrder, List<Product>> productMap = new HashMap<>();
//        Map<CustomerOrder, Integer> customerOrderCountMap  = new HashMap<>();
//        for (ProductCustomerOrder productCustomerOrder : productCustomerOrderList) {
//
//            customerOrderCountMap.put(productCustomerOrder.getCustomerOrder(),productCustomerOrder.getCount());
//
//            if(productMap.containsKey(productCustomerOrder.getCustomerOrder())){
//                productMap.get(productCustomerOrder.getCustomerOrder() ).add(productCustomerOrder.getProduct());
//            } else{
//                List<Product> newProductList = new ArrayList<>();
//                newProductList.add(productCustomerOrder.getProduct());
//                productMap.put(productCustomerOrder.getCustomerOrder(),newProductList );
//            }
//
//        }
//        for (Map.Entry<CustomerOrder, List<Product>> customerOrderListEntry : productMap.entrySet()) {
//            CustomerOrder customerOrder = customerOrderListEntry.getKey();
//            int count = customerOrderCountMap.get(customerOrder);
//            Double billCustomerOrder = customerOrderListEntry.getValue().stream()
//                    .mapToDouble(product -> product.getPrice().doubleValue() * count).sum();
//
//
//            HistoryOrder historyOrder = new HistoryOrder(customerOrder.getCustomer(),
//                    customerOrderListEntry.getValue(), customerOrder.getStatus(),customerOrder.getDate(),
//                    customerOrder.getDateUpdateStatus(),BigDecimal.valueOf(billCustomerOrder),new Rate());
////                    productCustomerOrderList.get(0).getProductTrader().getRate() );
//            historyOrderList.add(historyOrder);
//        }
//
//       return historyOrderList;
//    };
}


