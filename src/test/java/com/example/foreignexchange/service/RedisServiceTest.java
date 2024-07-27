package com.example.foreignexchange.service;

import com.example.foreignexchange.service.impl.RedisServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.github.javafaker.Faker;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RedisServiceTest {
    private static Faker faker;
    @Mock
    private RedisTemplate<String, String> redisTemplate;
    @Mock
    private ValueOperations valueOperations;
    @InjectMocks
    private RedisServiceImpl redisService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    void should_set_the_value(){
        // Arrange
        String key = faker.lorem().word();
        String value = faker.lorem().sentence();

        // Act
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        redisService.setValue(key, value);

        // Assert
        verify(valueOperations, times(1)).set(key, value);
    }

    @Test
    void should_get_the_value(){
        // Arrange
        String key = faker.lorem().word();
        String expectedValue = faker.lorem().sentence();

        // Act
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(key)).thenReturn(expectedValue);

        String actualValue = redisService.getValue(key);

        // Assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void should_delete_the_value() {
        // Arrange
        String key = faker.lorem().word();

        // Act
        redisService.deleteValue(key);

        // Assert
        verify(redisTemplate, times(1)).delete(key);
    }
}
