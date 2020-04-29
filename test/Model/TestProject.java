package Model;

import org.junit.jupiter.api.Test;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class TestProject {

    @Test
    public void creation(){

        //Creation
        User user1 = new User();
        user1.setUsername("User1");

        User user2 = new User();
        user2.setUsername("User2");

        Project project = new Project(1, user1);

        //Check of the default title
        assertEquals(project.getTitle(),"Unnamed");

        //Information setup

        project.setTitle("Premier projet");
        project.addCollaborator(user2);
        project.setCode("code");

        //Check of the setup
        assertEquals(project.getID(), 1);
        assertEquals(project.getTitle(), "Premier projet");
        assertEquals(project.getCreator().getUsername(), "User1");
        assertEquals(project.getCollaborators().get(0).getUsername(), "User2");
        assertEquals(project.getCode(), "code");

    }
}
