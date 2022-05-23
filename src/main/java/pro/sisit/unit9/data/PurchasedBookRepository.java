package pro.sisit.unit9.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pro.sisit.unit9.entity.Book;
import pro.sisit.unit9.entity.PurchasedBook;

import java.math.BigDecimal;
import java.util.List;

public interface PurchasedBookRepository extends CrudRepository<PurchasedBook, Long>, JpaRepository<PurchasedBook, Long> {

    @Query("select sum(pb.cost) from "
            + "PurchasedBook pb "
            + "join pb.book "
            + "where pb.book.title = ?1")
    BigDecimal sumPurchasedBooksByBookTitle(String title);

    @Query("select sum(pb.cost) from "
            + "PurchasedBook pb "
            + "join pb.customer "
            + "where pb.customer.name = ?1")
    BigDecimal sumPurchasedBooksByCustomerName(String customerName);

}
