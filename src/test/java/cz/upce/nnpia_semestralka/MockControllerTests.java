package cz.upce.nnpia_semestralka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

/*@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@ComponentScan*/
//@ExperimentalStdlibApi

class MockControllerTests{


        @Autowired
        MockMvc mvc;

        @Test
        void hds_getProjectList() throws Exception {
            /*    ResultActions resultActions = mvc.perform("/api/film")
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content", hasSize(2)));*/

              /*  MockMvcRequestBuilders
                        .post("/employees")
                        .content(asJsonString(new EmployeeVO(null, "firstName4", "lastName4", "email4@mail.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").exists());
        }*/
   /*     @Autowired constructor(
                private val mockMvc: MockMvc,
                private val creator: Creator
        ) {

            @Test
            fun filmGetAll() {
                val film1 = Film(name = "Film1")
                val film2 = Film(name = "Film2")
                creator.save(film1)
                creator.save(film2)

                this.mockMvc.perform(
                                get("/api/film")
                                        .accept(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk)
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            }

            @Test
            fun cinemaDetailAll() {
                val cinema1 = creator.save(Cinema(name = "Cinema1")) as Cinema
                val cinema2 = creator.save(Cinema(name = "Cinema2")) as Cinema

                this.mockMvc.perform(
                                get("/api/cinema/${cinema1.id}")
                                        .accept(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk)
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
           */ }

        }
