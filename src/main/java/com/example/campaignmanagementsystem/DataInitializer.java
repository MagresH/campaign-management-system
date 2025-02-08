package com.example.campaignmanagementsystem;

import com.example.campaignmanagementsystem.keyword.KeywordService;
import com.example.campaignmanagementsystem.location.LocationDTO;
import com.example.campaignmanagementsystem.location.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final KeywordService keywordService;
    private final LocationService locationService;

    public DataInitializer(
            KeywordService keywordService,
            LocationService locationService) {
        this.keywordService = keywordService;
        this.locationService = locationService;
    }

    @Override
    public void run(String... args) {
        List<String> keywords = Arrays.asList(
                "technology", "gadget", "smartphone", "laptop", "audio", "television", "camera", "gaming", "accessories", "innovation",
                "smartwatch", "robotics", "ai", "virtual reality", "cloud computing", "wearables", "big data", "machine learning", "blockchain", "5G",
                "internet of things", "augmented reality", "data science", "quantum computing", "autonomous vehicles", "biotech", "drones", "cybersecurity",
                "devops", "iot", "big data", "voice assistant", "AI chips", "3d printing", "smart home", "smart devices", "edge computing", "cloud storage",

                "furniture", "kitchen", "home", "garden", "decor", "tools", "cleaning", "cooking", "lighting", "design", "appliances",
                "interior", "furnishing", "outdoor", "plant care", "bathroom", "bedroom", "living room", "modern", "rustic", "storage", "smart home",
                "furniture design", "kitchenware", "home renovation", "indoor plants", "organization", "interior design", "home improvement", "eco-friendly",
                "smart lighting", "decorative items", "vintage furniture", "minimalist", "home styling", "lighting fixtures", "bed linen", "comfort", "furnishing",
                "space-saving", "wooden furniture", "decorative accents", "storage solutions",

                "fashion", "clothing", "accessories", "jewelry", "shoes", "handbags", "watches", "sunglasses", "trend", "style",
                "casual wear", "formal wear", "streetwear", "vintage", "branded", "luxury", "t-shirt", "jeans", "jackets", "hats", "scarves", "suits",
                "coats", "boots", "sportswear", "athleisure", "leggings", "sweaters", "denim", "chinos", "polo shirts", "activewear", "swimwear", "lingerie",
                "footwear", "fashion accessories", "bags", "gloves", "belts", "necklaces", "bracelets", "rings", "brooches", "earrings", "hair accessories",

                "sports", "fitness", "outdoor", "camping", "cycling", "soccer", "skiing", "fishing", "running", "swimming",
                "hiking", "kayaking", "climbing", "surfing", "yoga", "workout", "gear", "equipment", "golf", "tennis", "basketball", "cricket",
                "rugby", "volleyball", "football", "ski poles", "water sports", "outdoor adventures", "motor sports", "trail running", "archery", "hiking boots",
                "camping gear", "snowboarding", "mountaineering", "waterproof", "crossfit", "trail bikes", "backpacking", "windsurfing", "paragliding", "fitness gear",

                "books", "literature", "fantasy", "thriller", "science", "history", "biography", "children", "textbooks", "guides",
                "novels", "poetry", "fiction", "non-fiction", "romance", "sci-fi", "mystery", "crime", "adventure", "young adult", "classics",
                "historical fiction", "comics", "graphic novels", "literary fiction", "memoirs", "biography", "autobiography", "self-help", "travel",
                "cooking", "children's books", "picture books", "e-books", "book club", "audiobooks", "book series", "book review", "fantasy books",
                "detective novels", "thriller books", "book recommendations", "book shop",

                "health", "beauty", "cosmetics", "skincare", "wellness", "makeup", "haircare", "fragrance", "anti-aging", "fitness",
                "spa", "detox", "weight loss", "nutrition", "organic beauty", "natural", "facial", "body care", "acne treatment", "shampoo", "serums",
                "moisturizer", "cleanser", "sunscreen", "sun care", "eye cream", "hair masks", "face masks", "eyebrow pencil", "lip balm", "makeup remover",
                "cosmetic surgery", "fitness classes", "organic skincare", "holistic health", "meditation", "holistic beauty", "clean beauty", "spa products",
                "anti-wrinkle", "skin hydration", "toothpaste", "hair color", "vitamins", "nutritional supplements", "mental health", "exercise routine",

                "automotive", "motors", "car accessories", "parts", "motorcycles", "electric vehicles", "car maintenance", "tires", "engine",
                "wheels", "vehicle", "car repair", "auto parts", "racing", "driving", "sports car", "sedan", "SUV", "hybrid", "electric cars", "car insurance",
                "car service", "car detailing", "motorbike", "car battery", "motorcycle helmet", "GPS", "headlights", "car seats", "rims", "car stereo",
                "performance parts", "engine oil", "motoring", "car tires", "car washing", "automotive technology", "car brands", "car modifications",
                "vehicle maintenance", "car detailing products", "car workshops", "motorcycles accessories", "auto repair", "car leasing", "sports bike",

                "hobbies", "modeling", "collectibles", "instruments", "photography", "music", "movies", "games", "consoles", "software",
                "board games", "collecting", "painting", "drawing", "guitar", "piano", "drums", "videography", "comics", "anime", "films",
                "video games", "photography gear", "art", "guitar accessories", "music festivals", "jazz", "pop", "rock", "concerts", "film production",
                "scriptwriting", "video editing", "creative writing", "vlogging", "piano lessons", "digital art", "painting supplies", "art gallery",
                "e-sports", "dancing", "photo editing", "photography tutorials", "instrument lessons", "comedy", "theater",

                "toys", "kids", "babies", "education", "educational games", "learning", "parenting", "play", "homework", "studies",
                "mathematics", "science experiments", "language", "arts", "preschool", "school", "teachers", "students", "curriculum", "tutoring",
                "math tutor", "reading", "writing", "learning tools", "interactive learning", "online courses", "study materials", "education programs",
                "child development", "school supplies", "homeschooling", "study tips", "school activities", "language learning", "test preparation",
                "language arts", "early education", "math games", "children's education", "play learning", "high school",

                "pets", "dogs", "cats", "aquariums", "birds", "pet food", "pet care", "dog training", "cat toys", "fish tank",
                "pet grooming", "pet adoption", "hamsters", "rabbits", "pet accessories", "pet supplies", "pet behavior", "bird cage", "dog leash",
                "dog collar", "fish food", "cat grooming", "pet insurance", "pet toys", "pet clothing", "dog treats", "cat litter", "pet training",
                "pet products", "dog walkers", "dog adoption", "dog health", "petsitting", "pet behavior training", "pet rescue",

                "food", "beverages", "snacks", "organic", "vegan", "coffee", "tea", "breakfast", "lunch", "dinner",
                "desserts", "smoothies", "fruits", "vegetables", "cooking", "baking", "gluten-free", "fast food", "diet", "healthy food",
                "snack foods", "fresh produce", "gourmet food", "recipe", "food delivery", "restaurants", "dining", "alcohol", "craft beer", "wine",
                "smoothie recipes", "baking supplies", "organic snacks", "health supplements", "vegan food", "protein shakes", "chocolate", "sweets",
                "gourmet snacks", "cheese", "coffee beans", "meals", "food blog", "vegan snacks",

                "business", "industry", "tools", "safety", "packaging", "office supplies", "entrepreneurship", "startup", "marketing",
                "strategy", "finance", "investment", "consulting", "leadership", "project management", "business growth", "branding", "corporate",
                "online business", "B2B", "e-commerce", "business plan", "business solutions", "enterprise", "management", "HR", "teamwork",
                "small business", "corporate leadership", "business coaching", "market research", "business development", "digital marketing",
                "fintech", "social media marketing", "content marketing", "sales", "SEO", "PPC", "business opportunities", "profit growth",

                "offer", "promotion", "new", "bestseller", "clearance", "deal", "discount", "sale", "flash sale", "limited time",
                "special offer", "hot deal", "coupon", "free shipping", "giveaway", "clearance sale", "bundle", "buy one get one", "offer ends soon",
                "exclusive", "promotion code", "discount code", "special discount", "seasonal sale", "limited edition", "sale event",
                "bargain", "offer price", "buy more save more", "deal of the day", "flash offer", "best deal", "clearance discounts"
        );

        keywordService.findOrCreateByValues(keywords);

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
