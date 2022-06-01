package pro.sisit.unit9.service;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sisit.unit9.data.BookRepository;
import pro.sisit.unit9.data.CustomerRepository;
import pro.sisit.unit9.data.PurchasedBookRepository;
import pro.sisit.unit9.data.PurchasedBookSpecifications;
import pro.sisit.unit9.entity.Book;
import pro.sisit.unit9.entity.Customer;
import pro.sisit.unit9.entity.PurchasedBook;

@Service
@RequiredArgsConstructor
public class PurchaseBookService {

    private final PurchasedBookRepository purchasedBookRepository;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public void purchaseBook(String title, String customerName, BigDecimal price) {
        Book book = bookRepository.findFirstByTitle(title)
                                  .orElseThrow(() -> new RuntimeException(String.format(
                                      "Кинга '%s' не найдена",
                                      title
                                  )));

        Customer customer = customerRepository.findFirstByName(customerName)
                                              .orElseThrow(() -> new RuntimeException(String.format(
                                                  "Покупатель '%s' не найдена",
                                                  customerName
                                              )));

        PurchasedBook purchasedBook = new PurchasedBook();
        purchasedBook.setBook(book);
        purchasedBook.setCustomer(customer);
        purchasedBook.setCost(price);
        purchasedBookRepository.save(purchasedBook);
    }

    @Transactional
    public List<PurchasedBook> findAll() {

        return purchasedBookRepository.findAll();
    }

    @Transactional
    public BigDecimal totalCostOfBooksByBookTitle(String title) {
        return purchasedBookRepository.findAll(PurchasedBookSpecifications.bookTitleEquals(title))
                                      .stream()
                                      .map(PurchasedBook::getCost)
                                      .reduce(BigDecimal::add)
                                      .orElseThrow(() -> new RuntimeException(String.format(
                                          "Не найдена информация по книге с названием '%s'.",
                                          title
                                      )));

    }

    @Transactional
    public BigDecimal totalCostOfBooksByCustomerName(String customerName) {
        return purchasedBookRepository.findAll(PurchasedBookSpecifications.customerNameEquals(customerName))
                                      .stream()
                                      .map(PurchasedBook::getCost)
                                      .reduce(BigDecimal::add)
                                      .orElseThrow(() -> new RuntimeException(String.format(
                                          "Не найдена информация по покупателю с именем '%s'.",
                                          customerName
                                      )));

    }

}
