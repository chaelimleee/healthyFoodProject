package com.javateam.healthyFoodProject.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="upload_file_tbl")
public class UploadFile {
    
    @Id
    // @GeneratedValue
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    			    generator = "UPLOADFILE_SEQ_GENERATOR")
    @SequenceGenerator(
    	    name = "UPLOADFILE_SEQ_GENERATOR",
    	    sequenceName = "image_upload_file_seq",
    	    initialValue = 1,
    	    allocationSize = 1)
    @Column(name = "id")
    int id;
    
//    @OneToOne // 엔티티 간의 일대일 관계 매핑. 
//    @JoinTable(name = "photo_tbl",
//    			joinColumns = @JoinColumn(name = "id"),inverseJoinColumns = @JoinColumn(name = "board_num")) // 외부 키 지정.  id 컬럼을 외부 키로 사용하여 업로드파일 tbl과 연결. 
//    private PhotoVO photovo;

    @Column(name = "filename")
    String fileName;
    
    @Column(name="save_filename")
    String saveFileName;
    
    @Column(name="file_path")
    String filePath;
    
    @Column(name="content_type")
    String contentType;
    
    @Column(name="file_size")
    long fileSize;
    
    @Column(name="regdate")
    Date regDate;
    
}