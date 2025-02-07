package com.example.campaignmanagementsystem.location;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "location")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "town", nullable = false, unique = true)
    private String town;

    public static LocationBuilder builder() {
        return new LocationBuilder();
    }

    public static class LocationBuilder {
        private UUID id;
        private String town;

        LocationBuilder() {
        }

        public LocationBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public LocationBuilder town(String town) {
            this.town = town;
            return this;
        }

        public Location build() {
            return new Location(this.id, this.town);
        }

        public String toString() {
            return "Location.LocationBuilder(id=" + this.id + ", town=" + this.town + ")";
        }
    }
}
