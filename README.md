# quiz-application

1. **Microservices Architecture with Spring Boot:**
   - Utilized Spring Boot to develop a microservices-based application.
   - Implemented two microservices, the QuizService and QuestionService, following microservices architectural patterns to ensure modularity and scalability.
   - Employed Spring Cloud Eureka for service registration and discovery, allowing seamless communication between microservices.

2. **API Gateway with OpenFeign Client:**
   - Implemented an API Gateway to provide a unified entry point for the application, ensuring efficient routing and load balancing of requests.
   - Utilized OpenFeign clients for making HTTP requests between microservices, simplifying inter-service communication and enhancing maintainability.

3. **Elasticity in Question Handling:**
   - Developed the QuestionService to handle various operations, including retrieving all questions, categorizing questions, and generating quizzes based on user preferences.
   - Supported dynamic scalability by enabling the QuestionService to adjust to changing loads and maintain high availability.

4. **Quiz Creation and Assessment:**
   - Developed the QuizService to enable users to create quizzes, fetch questions for quizzes, and submit responses for quiz assessment.
   - Implemented functionality to calculate and return quiz scores based on user responses.
   - One of the key benefits of the application is that it provides an interactive platform for creating and taking quizzes, making it an ideal tool for educational and self-assessment purposes, promoting engagement and learning.
