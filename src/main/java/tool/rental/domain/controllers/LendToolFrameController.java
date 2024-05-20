package tool.rental.domain.controllers;

import tool.rental.domain.entities.Friend;
import tool.rental.domain.repositories.FriendRepository;
import tool.rental.presentation.LendToolFrame;
import tool.rental.utils.Controller;
import tool.rental.utils.ToastError;

import java.util.ArrayList;

public class LendToolFrameController extends Controller {
    private final FriendRepository friendRepository = new FriendRepository();

    public LendToolFrameController(LendToolFrame Frame){
        super(Frame);

    }

    public ArrayList<Friend> listFriendsAsComboBox() throws ToastError {
        return this.friendRepository.listAll();
    }
}