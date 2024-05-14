package tool.rental.domain.controllers;

import tool.rental.domain.use_cases.RegisterToolUseCase;
import tool.rental.utils.Controller;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;

public class RegisterToolController extends Controller{
    private final RegisterToolController registerToolController = new RegisterToolController();
    
    public RegisterToolController(PresentationFrame frame);
    super(frame);
   
}

public void registerTool(String name,String branch, double cost) throws ToastError {
        this.registerToolUseCase.execute(name, branch, cost);

        JOptionPane.showMessageDialog(null, "Ferramenta cadastrada com sucesso!");

        this.frame.setVisible(false);
    }
}
