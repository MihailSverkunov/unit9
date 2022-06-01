package pro.sisit.unit9;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pro.sisit.unit9.data.AuthorOfBookRepository;
import pro.sisit.unit9.data.AuthorRepository;
import pro.sisit.unit9.data.BookRepository;
import pro.sisit.unit9.data.CustomerRepository;
import pro.sisit.unit9.entity.Author;
import pro.sisit.unit9.entity.AuthorOfBook;
import pro.sisit.unit9.entity.Book;
import pro.sisit.unit9.entity.Customer;
import pro.sisit.unit9.service.PurchaseBookService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataApplicationTests {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AuthorOfBookRepository authorOfBookRepository;

	@Autowired
	private PurchaseBookService purchaseBookService;

	@Before
	public void init() {
		Book book = new Book();
		book.setDescription("Увлекательные приключения Тома Сойера");
		book.setTitle("Приключения Тома Сойера");
		book.setYear(1876);
		bookRepository.save(book);

		Book book2 = new Book();
		book2.setTitle("Михаил Строгов");
		book2.setYear(1876);
		bookRepository.save(book2);

		Book book3 = new Book();
		book3.setTitle("Приключения Гекльберри Финна");
		book3.setYear(1884);
		bookRepository.save(book3);

		Author author = new Author();
		author.setFirstname("Марк");
		author.setLastname("Твен");
		authorRepository.save(author);

		AuthorOfBook authorOfBook = new AuthorOfBook();
		authorOfBook.setAuthor(author);
		authorOfBook.setBook(book);
		authorOfBookRepository.save(authorOfBook);

		AuthorOfBook authorOfBook2 = new AuthorOfBook();
		authorOfBook2.setAuthor(author);
		authorOfBook2.setBook(book3);
		authorOfBookRepository.save(authorOfBook2);

		Author author2 = new Author();
		author2.setFirstname("Жюль");
		author2.setLastname("Верн");
		authorRepository.save(author2);

		AuthorOfBook authorOfBook3 = new AuthorOfBook();
		authorOfBook3.setAuthor(author2);
		authorOfBook3.setBook(book2);
		authorOfBookRepository.save(authorOfBook3);

		Book book4 = new Book();
		book4.setTitle("Буратино");
		book4.setYear(1936);
		bookRepository.save(book4);

		Author author3 = new Author();
		author3.setFirstname("Алексей");
		author3.setLastname("Толстой");
		authorRepository.save(author3);

		AuthorOfBook authorOfBook4 = new AuthorOfBook();
		authorOfBook4.setAuthor(author3);
		authorOfBook4.setBook(book4);
		authorOfBookRepository.save(authorOfBook4);


		Customer customer = new Customer();
		customer.setName("Иванов");
		customer.setAddress("Маркса 1");
		customerRepository.save(customer);

		Customer customer2 = new Customer();
		customer2.setName("Петров");
		customer2.setAddress("Мира 2");
		customerRepository.save(customer2);

		Customer customer3 = new Customer();
		customer3.setName("Сидоров");
		customer3.setAddress("Ленина 3");
		customerRepository.save(customer3);

		purchaseBookService.purchaseBook("Приключения Гекльберри Финна", "Иванов", BigDecimal.valueOf(10));
		purchaseBookService.purchaseBook("Михаил Строгов", "Иванов", BigDecimal.valueOf(200));

		purchaseBookService.purchaseBook("Приключения Тома Сойера", "Петров", BigDecimal.valueOf(1000));
		purchaseBookService.purchaseBook("Михаил Строгов", "Петров", BigDecimal.valueOf(300));

		purchaseBookService.purchaseBook("Приключения Гекльберри Финна", "Сидоров", BigDecimal.valueOf(20));
		purchaseBookService.purchaseBook("Михаил Строгов", "Сидоров", BigDecimal.valueOf(400));
		purchaseBookService.purchaseBook("Приключения Тома Сойера", "Сидоров", BigDecimal.valueOf(2000));

	}


	@Test
	@Transactional
	public void purchaseBook(){

		assertEquals(7, purchaseBookService.findAll().size());

	}

	@Test
	@Transactional
	public void totalCostOfBooksByTitle() {

		assertEquals(
			BigDecimal.valueOf(30),
			purchaseBookService.totalCostOfBooksByBookTitle("Приключения Гекльберри Финна")
		);
		assertEquals(BigDecimal.valueOf(900), purchaseBookService.totalCostOfBooksByBookTitle("Михаил Строгов"));
		assertEquals(
			BigDecimal.valueOf(3000),
			purchaseBookService.totalCostOfBooksByBookTitle("Приключения Тома Сойера")
		);

	}

	@Test
	@Transactional
	public void totalCostOfBooksByCustomerName() {

		assertEquals(BigDecimal.valueOf(210), purchaseBookService.totalCostOfBooksByCustomerName("Иванов"));
		assertEquals(BigDecimal.valueOf(1300), purchaseBookService.totalCostOfBooksByCustomerName("Петров"));
		assertEquals(BigDecimal.valueOf(2420), purchaseBookService.totalCostOfBooksByCustomerName("Сидоров"));

	}

	//	@Test
	//	public void testFindSame() {
	////		Book book = new Book();
	////		book.setYear(1876);
	////
	////		assertEquals(2, bookRepository.findAll(Example.of(book)).size());
	//	}
	//
	//	@Test
	//	public void testFindInRange() {
	////		assertEquals(3, bookRepository.findAll(
	////				BookSpecifications.byYearRange(1800, 1900)).size());
	////		assertEquals(0, bookRepository.findAll(
	////				BookSpecifications.byYearRange(2000, 2010)).size());
	//	}
	//
	//	@Test
	//	public void testFindByAuthorLastname() {
	////		assertTrue(bookRepository.findByAuthor("Верн")
	////				.stream().allMatch(book -> book.getTitle().equals("Михаил Строгов")));
	//	}
	//
	//	@Test
	//	public void testComplexQueryMethod() {
	////		assertEquals(4, bookRepository.complexQueryMethod().size());
	//	}

}
