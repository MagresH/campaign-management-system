package com.example.campaignmanagementsystem;

import com.example.campaignmanagementsystem.account.AccountService;
import com.example.campaignmanagementsystem.keyword.KeywordService;
import com.example.campaignmanagementsystem.location.LocationDTO;
import com.example.campaignmanagementsystem.location.LocationService;
import com.example.campaignmanagementsystem.product.ProductDTO;
import com.example.campaignmanagementsystem.product.ProductService;
import com.example.campaignmanagementsystem.seller.SellerDTO;
import com.example.campaignmanagementsystem.seller.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final SellerService sellerService;
    private final ProductService productService;
    private final KeywordService keywordService;
    private final LocationService locationService;
    private final AccountService accountService;

    public DataInitializer(SellerService sellerService,
                           ProductService productService,
                           KeywordService keywordService,
                           LocationService locationService,
                           AccountService accountService) {
        this.sellerService = sellerService;
        this.productService = productService;
        this.keywordService = keywordService;
        this.locationService = locationService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        SellerDTO seller1 = sellerService.createSeller(new SellerDTO(null, "Tech Solutions"));
        SellerDTO seller2 = sellerService.createSeller(new SellerDTO(null, "Home & Garden Co."));
        SellerDTO seller3 = sellerService.createSeller(new SellerDTO(null, "Fashion Hub"));
        SellerDTO seller4 = sellerService.createSeller(new SellerDTO(null, "Sporty Gear"));
        SellerDTO seller5 = sellerService.createSeller(new SellerDTO(null, "Bookworm Haven"));

        accountService.createAccount(seller1.id(), BigDecimal.valueOf(5000));
        accountService.createAccount(seller2.id(), BigDecimal.valueOf(3000));
        accountService.createAccount(seller3.id(), BigDecimal.valueOf(4000));
        accountService.createAccount(seller4.id(), BigDecimal.valueOf(3500));
        accountService.createAccount(seller5.id(), BigDecimal.valueOf(4500));

        List<ProductDTO> products = new ArrayList<>();

        products.add(new ProductDTO(null, "Smartphone X200", "State-of-the-art smartphone with advanced features", BigDecimal.valueOf(1200)));
        products.add(new ProductDTO(null, "Laptop ProBook 15", "High-performance laptop for professionals", BigDecimal.valueOf(2500)));
        products.add(new ProductDTO(null, "Wireless Headphones", "High-quality sound without the wires", BigDecimal.valueOf(400)));
        products.add(new ProductDTO(null, "4K Ultra HD TV", "Crystal-clear picture quality in 4K resolution", BigDecimal.valueOf(3000)));
        products.add(new ProductDTO(null, "ActionCam Sport Camera", "Capture every moment in motion", BigDecimal.valueOf(800)));

        products.add(new ProductDTO(null, "Outdoor Furniture Set", "Stylish set for your garden", BigDecimal.valueOf(1800)));
        products.add(new ProductDTO(null, "MultiChef Kitchen Robot", "Your indispensable kitchen assistant", BigDecimal.valueOf(700)));
        products.add(new ProductDTO(null, "Cordless Vacuum Cleaner", "Effortless cleaning without cords", BigDecimal.valueOf(600)));
        products.add(new ProductDTO(null, "Barista Coffee Maker", "Experience professional coffee at home", BigDecimal.valueOf(1500)));
        products.add(new ProductDTO(null, "Stainless Steel Cookware Set", "Durable pots for demanding chefs", BigDecimal.valueOf(500)));

        products.add(new ProductDTO(null, "Elegant Evening Dress", "Classic dress for special occasions", BigDecimal.valueOf(300)));
        products.add(new ProductDTO(null, "Runner Sports Shoes", "Comfort and style for running", BigDecimal.valueOf(250)));
        products.add(new ProductDTO(null, "Classic Leather Handbag", "Elegant handbag for every woman", BigDecimal.valueOf(450)));
        products.add(new ProductDTO(null, "Men's Chrono Watch", "Stylish watch for the modern man", BigDecimal.valueOf(800)));
        products.add(new ProductDTO(null, "UV400 Sunglasses", "Protect your eyes and look fashionable", BigDecimal.valueOf(200)));

        products.add(new ProductDTO(null, "TrailBlazer Mountain Bike", "Conquer every trail with ease", BigDecimal.valueOf(2000)));
        products.add(new ProductDTO(null, "Pro Soccer Ball", "Official match ball", BigDecimal.valueOf(150)));
        products.add(new ProductDTO(null, "4-Person Camping Tent", "Comfortable shelter for any adventure", BigDecimal.valueOf(600)));
        products.add(new ProductDTO(null, "Trekking Poles", "Support during mountain hikes", BigDecimal.valueOf(120)));
        products.add(new ProductDTO(null, "Longboard Skateboard", "Smooth ride around the city", BigDecimal.valueOf(350)));

        products.add(new ProductDTO(null, "The Return of the King - J.R.R. Tolkien", "The final part of the epic saga", BigDecimal.valueOf(50)));
        products.add(new ProductDTO(null, "Harry Potter and the Philosopher's Stone - J.K. Rowling", "The beginning of a magical journey", BigDecimal.valueOf(40)));
        products.add(new ProductDTO(null, "The Da Vinci Code - Dan Brown", "A fascinating thriller full of mysteries", BigDecimal.valueOf(45)));
        products.add(new ProductDTO(null, "Metro 2033 - Dmitry Glukhovsky", "Post-apocalyptic struggle for survival", BigDecimal.valueOf(55)));
        products.add(new ProductDTO(null, "Crime and Punishment - Fyodor Dostoevsky", "A psychological tale of guilt and redemption", BigDecimal.valueOf(35)));

        for (ProductDTO product : products) {
            productService.createProduct(product);
        }

        List<String> keywords = Arrays.asList(
                "technology", "gadget", "smartphone", "laptop", "audio",
                "television", "camera", "gaming", "accessories", "innovation",
                "furniture", "kitchen", "home", "garden", "decor",
                "tools", "cleaning", "cooking", "lighting", "design",
                "fashion", "clothing", "accessories", "jewelry", "shoes",
                "handbags", "watches", "sunglasses", "trend", "style",
                "sports", "fitness", "outdoor", "camping", "cycling",
                "soccer", "skiing", "fishing", "running", "swimming",
                "books", "literature", "fantasy", "thriller", "science",
                "history", "biography", "children", "textbooks", "guides",
                "health", "beauty", "cosmetics", "skincare", "wellness",
                "automotive", "motors", "car accessories", "parts", "motorcycles",
                "electronics", "appliances", "audio equipment", "smart home", "innovation",
                "hobbies", "modeling", "collectibles", "instruments", "photography",
                "music", "movies", "games", "consoles", "software",
                "toys", "kids", "babies", "education", "educational games",
                "pets", "dogs", "cats", "aquariums", "birds",
                "food", "beverages", "snacks", "organic", "vegan",
                "business", "industry", "tools", "safety", "packaging",
                "offer", "promotion", "new", "bestseller", "clearance"
        );

        keywordService.findOrCreateByValues(keywords);

        // Create locations (if needed)
        List<String> towns = Arrays.asList(
                "New York", "Los Angeles", "Chicago", "Houston", "Phoenix",
                "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose",
                "Austin", "Jacksonville", "Fort Worth", "Columbus", "Charlotte",
                "Indianapolis", "San Francisco", "Seattle", "Denver", "Washington"
        );

        for (String town : towns) {
            locationService.createLocation(new LocationDTO(null, town));
        }

        log.info("Data initialization completed.");
    }
}
