package com.example.campaignmanagementsystem.keyword;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "keyword")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "text", nullable = false, unique = true)
    private String text;

    public static KeywordBuilder builder() {
        return new KeywordBuilder();
    }

    public static class KeywordBuilder {
        private UUID id;
        private String text;

        KeywordBuilder() {
        }

        public KeywordBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public KeywordBuilder text(String text) {
            this.text = text;
            return this;
        }

        public Keyword build() {
            return new Keyword(this.id, this.text);
        }

        public String toString() {
            return "Keyword.KeywordBuilder(id=" + this.id + ", text=" + this.text + ")";
        }
    }
}
