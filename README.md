# Project with Custom ID Generation

Исследование механизмов Spring Data JDBC на основе официальной документации. 
Реализация кастомной генерации ID с использованием `BeforeConvertCallback`.

## 🎯 Цель проекта

Реализовать способ генерации ID вместо стандартных стратегий.

## 🛠️ Реализованная функциональность

- **REST API** с базовыми CRUD операциями
- **Кастомная генерация ID** через `BeforeConvertCallback`
- **Встроенная БД H2** для демонстрации
- **Использование WebMvcTest** для тестирования контроллеров 

## 🔧 Технические особенности

### Кастомная генерация ID
```java
@Bean
    public BeforeConvertCallback<Person> beforeConvertCallback() {
        return (person) -> {
            if (person.getId() == null) {
                person.setId(UUID.randomUUID().toString());
            }
            return person;
        };
    }
