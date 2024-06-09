package tool.rental.domain.use_cases;

import tool.rental.domain.entities.Friend;
import tool.rental.domain.entities.Tool;
import tool.rental.domain.repositories.RentalRepository;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.utils.ToastError;

import javax.swing.*;


import javax.swing.JOptionPane;

/**
 * This class represents a use case for renting a tool to a friend.
 */
public class RentalToolUseCase {
    private final RentalRepository rentalRepository = new RentalRepository(); // Repository for rentals
    private final ToolRepository toolRepository = new ToolRepository(); // Repository for tools

    /**
     * Executes the use case to rent a tool to a friend.
     *
     * @param friend The friend to whom the tool will be rented.
     * @param tool   The tool to be rented.
     * @throws ToastError if an error occurs during the execution.
     */
    public void execute(Friend friend, Tool tool) throws ToastError {
        long currentStamp = System.currentTimeMillis(); // Get the current timestamp

        // Check if the friend already has any tool rented
        if (toolRepository.isAnyToolRentedByFriend(friend)) {
            // Display a message informing that the selected friend already has tools borrowed
            JOptionPane.showMessageDialog(
                    null,
                    "O(A) amigo(a) selecionado(a) já possui ferramenta(s) emprestada(s)."
            );
        }

        // Display a message informing that the tool has been successfully rented
        JOptionPane.showMessageDialog(
                null,
                "Ferramenta emprestada com sucesso!"
        );

        // Create a new rental record for the tool and the friend in the repository
        this.rentalRepository.create(currentStamp, friend, tool);
    }
}

