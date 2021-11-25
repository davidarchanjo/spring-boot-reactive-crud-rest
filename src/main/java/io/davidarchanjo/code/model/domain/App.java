package io.davidarchanjo.code.model.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table("app")
public class App {

  @Id
  private Long id;

  @Column(value = "name")
  private String name;

  @Column(value = "author")
  private String author;

  @Column(value = "version")
  private String version;

  @Builder
  public App(Long id, String name, String author, String version) {
    this.id = id;
    this.name = name;
    this.author = author;
    this.version = version;
  }

}
