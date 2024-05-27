package tool.rental.domain.use_cases;

import tool.rental.domain.repositories.FriendRepository;
import tool.rental.utils.ToastError;
public class DeleteFriendUseCase {
    private final FriendRepository friendRepository = new FriendRepository();
   

    public void execute(String friendId) throws ToastError {
        if(!friendRepository.friendHasToolRented(friendId)){
            this.friendRepository.deleteFriend(friendId);
        }else{
        throw new ToastError(
                    "Não foi possível deletar o amigo pois o mesmo possui uma ferramenta em emprestimo.",
                    "Erro ao deletar."
        );}
    }
}
