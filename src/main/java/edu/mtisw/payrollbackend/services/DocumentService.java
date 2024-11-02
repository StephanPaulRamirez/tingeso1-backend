package edu.mtisw.payrollbackend.services;

import edu.mtisw.payrollbackend.entities.DocumentEntity;
import edu.mtisw.payrollbackend.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class DocumentService {
    @Autowired
    DocumentRepository documentRepository;

    public DocumentEntity saveDocument(MultipartFile file, Long loanRequestId, String type) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setName(fileName);
        byte[] fileContent = file.getBytes();
        documentEntity.setContent(fileContent);
        documentEntity.setType(type);
        documentEntity.setLoanRequestId(loanRequestId);
        return documentRepository.save(documentEntity);
    }

    public DocumentEntity getDocumentByLoanRequestId(Long loanRequestId, String type) throws FileNotFoundException {
        DocumentEntity documentEntity = documentRepository.findByLoanRequestIdAndType(loanRequestId, type);
        if (documentEntity != null) {
            return documentEntity;
        }
        throw new FileNotFoundException("Document not found for the provided loanRequestId and type.");
    }

}
