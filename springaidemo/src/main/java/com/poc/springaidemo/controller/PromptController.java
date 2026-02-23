package com.poc.springaidemo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PromptController {

    @Autowired
    private OpenAiChatModel chatClient;

    @Autowired
    OpenAiChatOptions chatOptions;
//13. Implement PromptChatMemoryAdvisor - send previous conversation history in the prompt
    private ChatClient chatClients;

    public PromptController(ChatClient.Builder builder) {
        ChatMemory chatMemory= MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .maxMessages(30)
                .build();
        this.chatClients=builder.defaultAdvisors(PromptChatMemoryAdvisor.builder(chatMemory).build()).build();
    }

    @PostMapping("/{conversationId}")
    public String home(@PathVariable String conversationId, @RequestBody String message) {
        return chatClients.prompt()
                .advisors(advisor -> advisor.param("conversationId", conversationId))
                .user(message)
                .call().content();
    }

    @GetMapping("/m1")
    public String message1() {
        return chatClient.call(new Prompt("Tell me a joke about Springboot")).getResult().getOutput().getText();
    }

    //with prompt messages
    //1. Global options via application.yml
    @GetMapping("/m2")
    public String message2() {
        var system = new SystemMessage("You are a senior Java and Springboot expert. Be precise and pragmatic");
        var assistant = new AssistantMessage("Dependency Injection lets Spring manage object wiring via the container");
        var user = new UserMessage("Give me a concise, real world example of DI in Springboot");
        var prompt = new Prompt(List.of(system, assistant, user));
        return chatClient.call(prompt).getResult().getOutput().getText();
    }

    //with prompt options
    @GetMapping("/m3")
    public String message3() {

        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .temperature(0.7)
                .maxTokens(100)
                .topP(0.9)
                .build();

        var system = new SystemMessage("You are a senior Java and Springboot expert. Be precise and pragmatic");
        var assistant = new AssistantMessage("Dependency Injection lets Spring manage object wiring via the container");
        var user = new UserMessage("Give me a concise, real world example of DI in Springboot");
        var prompt = new Prompt(List.of(system, assistant, user));
        return chatClient.call(prompt).getResult().getOutput().getText();
    }

    //2. Create global chatoptions bean and use it in the prompt
    @GetMapping("/m4")
    public String message4() {
        var system = new SystemMessage("You are a senior Java and Springboot expert. Be precise and pragmatic");
        var assistant = new AssistantMessage("Dependency Injection lets Spring manage object wiring via the container");
        var user = new UserMessage("Give me a concise, real world example of DI in Springboot");
        var prompt = new Prompt(List.of(system, assistant, user), chatOptions);
        return chatClient.call(prompt).getResult().getOutput().getText();
    }

    //parameterized prompt with placeholders, passing prompt at runtime
    @GetMapping("/popular")
    public String findPopularYoutubers(@RequestParam(value = "genre", defaultValue = "tech") String genre) {
        String message = """
                   List most popular Youtubers in {genre} along with their current subscribers counts. If you dont know'
                   the answer, just say "I dont know".
                """;
        PromptTemplate pt = new PromptTemplate(message);
        Prompt p = pt.create(Map.of("genre", genre));

        return chatClient.call(p).getResult().getOutput().getText();
    }

//Instead of creating same prompt multiple times, we can access it from the files (.st extension- String Template)
    //Inside resources - create prompts folder - create popular.st file
    //List most popular Youtubers in {genre} along with their current subscribers counts. If you dont know the answer, just say "I dont know".

    @Value("classpath:/prompts/popular.st")
    private Resource promptResource;

    @GetMapping("/popular2")
    public String findPopularYoutubers2(@RequestParam(value = "genre", defaultValue = "tech") String genre) {

        PromptTemplate pt = new PromptTemplate(promptResource);
        Prompt p = pt.create(Map.of("genre", genre));

        return chatClient.call(p).getResult().getOutput().getText();
    }

//    OutputConverters/OutputParsers
//    - Used to structure the response from LLM
//    - 3 implementations

    //1. ListOutputConverter - structure the response as List
    @GetMapping("/countries")
    public List<String> getCountries(@RequestParam(value = "country", defaultValue = "India") String country) {
        var message = """
                  Please give me a list of destinations for the country {country}. If you dont know the answer just say "I dont know".
                  {format}
                """;
        ListOutputConverter output = new ListOutputConverter(new DefaultConversionService());

        PromptTemplate pt = new PromptTemplate(message);
        Prompt p = pt.create(Map.of("country", country, "format", output.getFormat()));
        ChatResponse response = chatClient.call(p);

        return output.convert(response.getResult().getOutput().getText());
    }

    //2. MapOutputConverter - structure the response as Map
    @GetMapping("/authors/{author}")
    public Map<String, Object> getAuthors(@PathVariable("author") String author) {
        var message = """
                  Generate list of links for the author {author}. Include the author name as the key and any social network links as the value. If you dont know the answer just say "I dont know".
                  {format}
                """;
        MapOutputConverter map = new MapOutputConverter();
        String format = map.getFormat();
        PromptTemplate pt = new PromptTemplate(message);
        Prompt p = pt.create(Map.of("author", author, "format", format));
        ChatResponse response = chatClient.call(p);

        return map.convert(response.getResult().getOutput().getText());
    }

    //3. BeanOutputConverter
    public record Author(String author, List<String> books) {

    }

    @GetMapping("/byAuthor")
    public Author getAuthorByBook(@RequestParam(value = "author", defaultValue = "Ken Kousen") String author) {
        var message = """
                  Generate a list of books written by the author {author}. If you arent positive that the book belong to this author please dont include it.
                  {format}
                """;

        BeanOutputConverter<Author> output = new BeanOutputConverter<Author>(Author.class);
        String format = output.getFormat();
        PromptTemplate pt = new PromptTemplate(message);
        Prompt p = pt.create(Map.of("author", author, "format", format));
        ChatResponse response = chatClient.call(p);

        return output.convert(response.getResult().getOutput().getText());
    }


//3 types of Prompt
//1. Zero shot prompting
// -You ask the model to do a task without giving any examples. The model relies only on the pretrained data
//
// When?
//- Task is simple or common
//- you want quick results
//- No strict output format required

    // Eg:Prompt: Explain REST API in simple terms
    @GetMapping("/explain")
    public String message() {
        var msg = new UserMessage("Explain kafka in simple terms");

        return chatClient.call(new Prompt(msg)).getResult().getOutput().getText();
    }

//2. Few shot prompting
//- You provide few examples of input and output and then ask the model to do similar task
//When?
//1. consistent format
//2. domain specific

    @GetMapping("/explain5")
    public String message5() {
        String msg = """
                   You are an AI that generated Java interview questions.
                   Example:
                   Topic: OOP
                   Question: What is encapsulation in Java?
                  
                   Example:
                   Topic: Collections
                   Question: What is ArrayList in Java?                      
                   Now generate a question for:
                   Topic: Multithreading
                """;

        return chatClient.call(new Prompt(msg)).getResult().getOutput().getText();
    }

//3. Chain of Thoughts(CoT) prompting
//- You ask the model to show its reasoning steps, not just the final answer
    //When?
    // - Logical reasoning
//- Debugging
//- Math/decision making

    @GetMapping("/explain6")
    public String message6() {
        var message = new UserMessage("""
                An application is slow during peak hours.
                Think step by step and identify possible causes.
                """);

        return chatClient.call(new Prompt(message)).getResult().getOutput().getText();
    }
}
