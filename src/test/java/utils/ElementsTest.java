package utils;

import boardifier.model.Model;
import boardifier.model.TextElement;
import model.KoRStageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ElementsTest {

    private KoRStageModel stageModel;
    private Model model;

    @BeforeEach
    void createStageModel() {
        // Create the model
        model = new Model();
        model.addHumanPlayer("player1");
        model.addHumanPlayer("player2");

        stageModel = new KoRStageModel("", model);
        stageModel.getDefaultElementFactory().setup();
    }

    @Test
    void updateTextTest() {
        final TextElement textElement = new TextElement("1", stageModel);
        Elements.updateText(textElement, 2);
        assertEquals("2", textElement.getText());
    }

    @Test
    void updateTextTest2() {
        final TextElement textElement = new TextElement("1", stageModel);
        Elements.updateText(textElement, -1);
        assertEquals("-1", textElement.getText());
    }

    @Test
    void updateTextTestBlank() {
        final TextElement textElement = new TextElement("1", stageModel);
        Elements.updateText(textElement, 0);
        assertTrue(textElement.getText().isEmpty());
    }


}
