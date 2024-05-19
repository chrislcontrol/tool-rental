package tool.rental.domain.use_cases;

import tool.rental.domain.entities.Friend;
import tool.rental.domain.repositories.FriendRepository;
import tool.rental.utils.ToastError;

import java.util.ArrayList;

public class ListFriendsToMainTableUseCase {
    private final FriendRepository friendRepository = new FriendRepository();

    public String[][] execute()throws ToastError {
        ArrayList<Friend>friends = this.friendRepository.listAll();
        String[][] resultArray = new String[friends.size()][5];
        for (int i = 0; i < resultArray.length; i++){
            Friend friend = friends.get(i);

            resultArray[i][0] = friend.getId();
            resultArray[i][1] = friend.getName();
            resultArray[i][2] = friend.getPhone();
            resultArray[i][3] = friend.getSocialSecurity();
        }
        return resultArray;
    }
}
