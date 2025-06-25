package me.paramoza.microlink.data;

import me.paramoza.microlink.entity.UrlRedirect;
import org.springframework.data.repository.CrudRepository;

public interface UrlRepository extends CrudRepository<UrlRedirect, String> {
}
