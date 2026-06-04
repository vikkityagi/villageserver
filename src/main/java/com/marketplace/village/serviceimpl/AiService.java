package com.marketplace.village.serviceimpl;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.marketplace.village.tool.PriceTool;
import com.marketplace.village.tool.ProductTool;
import com.marketplace.village.tool.ShopTools;

@Service
public class AiService {

    private final ChatClient chatClient;

    private final ProductTool productTool;
    private final PriceTool priceTool;
    private final ShopTools shopTools;

    public AiService(
            ChatClient.Builder builder,
            ProductTool productTool,
            PriceTool priceTool,
            ShopTools shopTools) {

        this.chatClient = builder.build();

        this.productTool = productTool;
        this.priceTool = priceTool;
        this.shopTools = shopTools;
    }

    public String ask(String question) {

        return chatClient.prompt()
                .system("""
                        You are a marketplace assistant.

                        When a tool returns data:
                        - Use the tool result.
                        - Never describe the tool.
                        - Never explain what the function does.
                        - Show the actual products returned.
                        - If multiple products are returned, list all of them.
                        """)
                .tools(productTool, priceTool, shopTools)
                .user(question)
                .call()
                .content();
    }
}
