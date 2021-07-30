package sid.cider.em;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import sid.cider.em.CiderApplication;


@SpringBootApplication
public class CiderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CiderApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
    
        return new OpenAPI()
          .info(new Info()
	          .title("CIDER-EM API")
	          .version("1.0.0-oas3")
	          .description("CIDER-EM Context and Inference baseD ontology alignER. CIDER-EM is a word-embedding-based system for monolingual and cross-lingual ontology alignment deveoped by the SID group (University of Zaragoza). It evolves the CIDER-CL tool, deveoped at OEG (Universidad Politécnica de Madrid) and SID (University of Zaragoza) by including the use of word embeddings.")
	          .license(new License().name("GNU General Public License version 3").url("https://www.gnu.org/licenses/gpl-3.0.html"))
	      );
    }
}
