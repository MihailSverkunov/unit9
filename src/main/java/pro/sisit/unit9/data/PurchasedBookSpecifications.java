package pro.sisit.unit9.data;

import org.springframework.data.jpa.domain.Specification;
import pro.sisit.unit9.entity.PurchasedBook;

public class PurchasedBookSpecifications {

    public static Specification<PurchasedBook> bookTitleEquals(String title) {
        return (root, query, cb) -> cb.equal(root.join("book").get("title"), title);
    }

    public static Specification<PurchasedBook> customerNameEquals(String name) {
        return (root, query, cb) -> cb.equal(root.join("customer").get("name"), name);
    }

}
