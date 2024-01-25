package io.github.ardonplay.infopanel.server;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServerApplicationTest {
    @Test
    public void test(){
        int i =0;
        ++i;
        Assertions.assertEquals(1, i);
    }
}
