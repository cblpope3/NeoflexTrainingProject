# NeoflexTrainingProject

## Training project from neoflex.

### Задание. Разработка микросервиса на Spring.

- [x] 1 - Установка ПО:
  Установить Java 11 или выше. Скачать и установить Intellij IDEA Community Version. Скачать и установить Postman.
- [x] 2 - Через Spring Initializer https://start.spring.io/ сделать проект с параметрами: Java 11, сборщик maven, подключить
  зависимости для Postgre SQL Driver.
- [x] 3 - Реализовать простой MVC проект с набором классов: Controller, Service. Проект должен уметь на
  запрос http://localhost:8080/test возвращать ответ "OK". Пример можно
  посмотреть https://www.baeldung.com/spring-controllers
- [x] 4 - Продумывание начальной бизнес-логики приложения. Задача от бизнеса. Реализовать систему обеспечения рабочего
  процесса, который выглядит следующим образом: для выполнения рабочей операции нужно предоставить работника, материал,
  соответствующие технологической карте процесса.
    - [x] 4.1 - Нужно продумать, какие нужны таблицы в БД для сущностей "работник", "материал", "технологическая карта", "
      завершенный процесс".
    - [x] 4.2 - Подключить БД на Postgre SQL к проекту. Вручную завести в БД таблицы и тестовые данные.
    - [x] 4.3 - Добавить в проект классы Entity соответствующие таблицам в БД. Добавить классы Repository для работы с
      таблицами БД. Добавить классы Service для обращения к репозиториям. Добавить классы Controller, в которых будут
      реализованы CRUD-операции для сущностей "работник", "материал", "технологическая карта".
    - [x] 4.4 - Подключить в проект Swagger. Настроить описание методов контроллеров, чтобы через Swagger можно было получить
      полную информацию об операции, передаваемых параметрах, структуре ответа.
- [x] 5 - Добавление логики для выполнения технологической операции. Добавить классы Controller, Service, Repository, Entity
  для сущности "завершенный процесс".
    - [x] 5.1 - На вход должен передаваться запрос, идентификатор работника, материал, идентификатор технологической карты.
      Надо проработать структуру этого запроса в виде JSON.
    - [x] 5.2 - Реализовать бизнес-логику выполнения процесса: если сотрудник есть в БД, материал есть в БД и удовлетворяет
      требованиям технологической карты, то процесс выполняется успешно, иначе - неуспешно.
- [x] 6 - Добавление логирования. Подключить в проект библиотеку log4j. Процесс выполнения технологической операции из п.5
  должен записываться в лог: ошибки выполнения, результат выполнения "успешно"/"неуспешно".
- [x] 7 - Добавление маппинга. Для сущностей "работник", "материал", "технологическая карта", "завершенный процесс"
  необходимо создать классы DTO (data transfer object), которые должны приходить на вход в контроллеры, маппиться в
  Entity. На выходе из контроллеров Entity должны обратно преобразовываться в DTO и отправляться в качестве ответа на
  запрос. Как правило, это необходимо для возможности сокрытия части внутренней логики, или же наоборот, добавления
  дополнительной информации, отсутствующей в Entity. Для этого в проект нужно подключить маппер MapStruct и делать в нем
  преобразование Entity -> DTO и DTO -> Entity без изменений полей.
- [ ] 8 - Добавить работу через планировщик:
  Убрать из контроллера сущности "завершенный процесс" метод создания процесса. Добавить класс-планировщик, который
  будет рандомно запускать технологический процесс. Для этого нужно воспользоваться аннотацией Scheduled и
  Cron-выражение для указание периодичности запуска.
- [ ] 9 - Добавить в контроллер сущности "завершенный процесс" метод формирования отчета по завершенным технологическим
  процессам.

Примечания:

1. Перед каждым пунктом рекомендуется связаться с куратором для обсуждения реализации.
2. После выполнения каждого пункта создается мердж-реквест в репозитории и отправляется на проверку куратору.
