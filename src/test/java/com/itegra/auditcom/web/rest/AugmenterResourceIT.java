package com.itegra.auditcom.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.itegra.auditcom.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Test class for the AugmenterResource REST controller.
 *
 * @see AugmenterResource
 */
@IntegrationTest
class AugmenterResourceIT {

    private MockMvc restMockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        AugmenterResource augmenterResource = new AugmenterResource();
        restMockMvc = MockMvcBuilders.standaloneSetup(augmenterResource).build();
    }

    /**
     * Test augmenter
     */
    @Test
    void testAugmenter() throws Exception {
        restMockMvc.perform(post("/api/augmenter/augmenter")).andExpect(status().isOk());
    }
}
