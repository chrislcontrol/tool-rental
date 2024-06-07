package tool.rental.domain.use_cases;

import tool.rental.domain.entities.Friend;
import tool.rental.utils.ToastError;

import java.util.ArrayList;
import java.util.List;

public class ListFriendsToMainTableUseCase {
    private final FriendRepository friendRepository = new FriendRepository();

    public List<String[]> execute() throws ToastError {
        ArrayList<Friend> friends = this.friendRepository.listAll();
        ArrayList<String[]> resultArray = new ArrayList<>(friends.size());

        for (Friend friend : friends) {
            String[] row = {
                    friend.getId(),
                    friend.getName(),
                    friend.getPhone(),
                    friend.getSocialSecurity()
            };
            resultArray.add(row);
        }

        resultArray.trimToSize();

        return resultArray;
    }

    public Friend getFriendById(String friendId) throws ToastError {
        return this.friendRepository.getById(friendId);
    }
}
