package edu.mtisw.payrollbackend.controllers;

import edu.mtisw.payrollbackend.entities.DocumentEntity;
import edu.mtisw.payrollbackend.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/documents")
@CrossOrigin("*")
public class DocumentController {
    @Autowired
    DocumentService documentService;


    @GetMapping("/getByLoanId/{loanRequestId}")
    public ResponseEntity<byte[]> getDocumentByLoanRequestId(@PathVariable Long loanRequestId,
                                                       @RequestParam String type) throws FileNotFoundException {
        DocumentEntity document = documentService.getDocumentByLoanRequestId(loanRequestId, type);

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getName() + "\"")
                .body(document.getContent());
    }

    @PostMapping("/upload")
    public ResponseEntity<DocumentEntity> saveDocument(@RequestParam("file") MultipartFile file,
                                                       @RequestParam("loanRequestId") Long loanRequestId,
                                                       @RequestParam("type") String type) throws IOException {
        DocumentEntity documentNew = documentService.saveDocument(file, loanRequestId, type);
        return ResponseEntity.status(HttpStatus.OK).body(documentNew);
    }

}
