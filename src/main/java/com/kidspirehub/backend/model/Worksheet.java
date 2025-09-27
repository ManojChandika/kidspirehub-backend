package com.kidspirehub.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "worksheets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Worksheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Size(max = 50)
    private String grade;

    @Size(max = 50)
    private String subject;

    @Size(max = 50)
    private String resourceType;

    private String fileUrl;

    @ElementCollection
    @CollectionTable(name = "worksheet_preview_images", joinColumns = @JoinColumn(name = "worksheet_id"))
    @Column(name = "image_url")
    private List<String> previewImages;
}


