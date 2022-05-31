package pro.sisit.unit9.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import pro.sisit.unit9.entity.PurchasedBook;

public interface PurchasedBookRepository
    extends CrudRepository<PurchasedBook, Long>, JpaRepository<PurchasedBook, Long>,
            JpaSpecificationExecutor<PurchasedBook> {


}
