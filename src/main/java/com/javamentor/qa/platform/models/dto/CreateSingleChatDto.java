package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.chat.ChatType;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.models.util.OnCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSingleChatDto implements Serializable {

    @NotNull(groups = OnCreate.class, message = "Значение title отсутствует")
    @NotBlank(groups = OnCreate.class, message = "Значение title не должно быть пустым")
    @NotNull String title;

    @NotNull(groups = OnCreate.class, message = "Значение UserRecipient отсутствует")
    private Long userRecipientId;

    private Long userSenderId;

    private ChatType chatType = ChatType.SINGLE;

    String message;
}
