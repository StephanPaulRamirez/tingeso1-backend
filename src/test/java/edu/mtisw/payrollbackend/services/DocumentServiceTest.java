package edu.mtisw.payrollbackend.services;

import edu.mtisw.payrollbackend.entities.DocumentEntity;
import edu.mtisw.payrollbackend.repositories.DocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentService documentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveDocument_Success() throws IOException {
        // Crear un archivo de prueba
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.txt", "text/plain", "Test content".getBytes()
        );

        DocumentEntity document = new DocumentEntity();
        document.setId(1L);
        document.setName(file.getOriginalFilename());
        document.setContent(file.getBytes());
        document.setType("ID");
        document.setLoanRequestId(1L);

        when(documentRepository.save(any(DocumentEntity.class))).thenReturn(document);

        DocumentEntity savedDocument = documentService.saveDocument(file, 1L, "ID");

        assertNotNull(savedDocument);
        assertEquals("test.txt", savedDocument.getName());
        assertEquals("ID", savedDocument.getType());
        assertEquals(1L, savedDocument.getLoanRequestId());
        verify(documentRepository, times(1)).save(any(DocumentEntity.class));
    }

    @Test
    void testGetDocumentByLoanRequestId_Success() throws FileNotFoundException {
        DocumentEntity document = new DocumentEntity();
        document.setId(1L);
        document.setLoanRequestId(1L);
        document.setType("ID");

        when(documentRepository.findByLoanRequestIdAndType(1L, "ID")).thenReturn(document);

        DocumentEntity foundDocument = documentService.getDocumentByLoanRequestId(1L, "ID");

        assertNotNull(foundDocument);
        assertEquals(1L, foundDocument.getLoanRequestId());
        assertEquals("ID", foundDocument.getType());
        verify(documentRepository, times(1)).findByLoanRequestIdAndType(1L, "ID");
    }

    @Test
    void testGetDocumentByLoanRequestId_DocumentNotFound() {
        when(documentRepository.findByLoanRequestIdAndType(1L, "ID")).thenReturn(null);

        assertThrows(FileNotFoundException.class, () -> documentService.getDocumentByLoanRequestId(1L, "ID"));
    }
}


