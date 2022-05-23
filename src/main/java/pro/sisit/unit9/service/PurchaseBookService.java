package pro.sisit.unit9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sisit.unit9.data.BookRepository;
import pro.sisit.unit9.data.CustomerRepository;
import pro.sisit.unit9.data.PurchasedBookRepository;
import pro.sisit.unit9.entity.Book;
import pro.sisit.unit9.entity.Customer;
import pro.sisit.unit9.entity.PurchasedBook;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseBookService {

    private final PurchasedBookRepository purchasedBookRepository;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;

    public void purchaseBook(String title, String customerName, BigDecimal price){
        Book book = bookRepository.findFirstByTitle(title)
                .orElseThrow(() ->  new RuntimeException(String.format("Кинга '%s' не найдена", title)));

        Customer customer = customerRepository.findFirstByName(customerName)
                .orElseThrow(() ->  new RuntimeException(String.format("Покупатель '%s' не найдена", customerName)));

        PurchasedBook purchasedBook = new PurchasedBook();
        purchasedBook.setBook(book);
        purchasedBook.setCustomer(customer);
        purchasedBook.setCost(price);
        purchasedBookRepository.save(purchasedBook);
    }

    public List<PurchasedBook> findAll(){

        return purchasedBookRepository.findAll();
    }

    public BigDecimal totalCostOfBooksByBookTitle(String bookTitle){
        return purchasedBookRepository.sumPurchasedBooksByBookTitle(bookTitle);

    }
    public BigDecimal totalCostOfBooksByCustomerName(String customerName){
        return purchasedBookRepository.sumPurchasedBooksByCustomerName(customerName);

    }


}
