[spring ai Day2-3.java](https://github.com/user-attachments/files/25379505/spring.ai.Day2-3.java)
[spring ai Day1.java](https://github.com/user-attachments/files/25379504/spring.ai.Day1.java)
[spring ai Day 6.java](https://github.com/user-attachments/files/25379503/spring.ai.Day.6.java)
[spring ai Day 5.java](https://github.com/user-attachments/files/25379502/spring.ai.Day.5.java)
[spring ai Day 4.java](https://github.com/user-attachments/files/25379501/spring.ai.Day.4.java)

Handson Assignments

Handson 1
Create a Springboot service using SpringAI that takes dynamic user input to generate a customized travel itinerary. Participants must demonstrate how to use a PromptTemplate to manage dynamic variables and a System message to define the persona

curl -X 'GET' \
  'http://localhost:8080/api/travel/itinerary?destination=pune&days=3&interests=food&budget=5000' \
  -H 'accept: */*'


Handson 2
You are building a Personal banking Advisor using Spring AI. The advisor must be able to retrieve the user’s current balance from a database(imagine we have db with all customer details) using tool calling


Handson 3
You are developing an AI Advisor for a corporate HR department. The advisor must meet 3 requirements

1. It must remember the users previous questions to handle follow up queries

2. All raw prompts and model responses must be logged for internal review

3. It must block any questions related to internal salary data or non-work topics