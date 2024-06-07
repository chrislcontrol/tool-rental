package tool.rental.domain.use_cases;

import tool.rental.domain.entities.Friend;
import tool.rental.domain.entities.Tool;
import tool.rental.domain.repositories.RentalRepository;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.utils.ToastError;

import javax.swing.*;


public class RentalToolUseCase {
    private final RentalRepository rentalRepository = new RentalRepository();
    private final ToolRepository toolRepository = new ToolRepository();

    public void execute(Friend friend, Tool tool) throws ToastError {
        long currentStamp = System.currentTimeMillis();
        if (toolRepository.isAnyToolRentedByFriend(friend)) {
            JOptionPane.showMessageDialog(
                    null,
                    "O(A) amigo(a) selecionado(a) já possui ferramenta(s) emprestada(s)."
            );
        }
        JOptionPane.showMessageDialog(
                null,
                "Ferramenta emprestada com sucesso!"
        );
        this.rentalRepository.create(currentStamp, friend, tool);
    }

}
