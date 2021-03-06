package com.google.sps;

import com.google.gson.Gson;
import com.google.sps.data.NonPersistentUserRepository;
import com.google.sps.data.User;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** */
@RunWith(JUnit4.class)
public final class NonPersistentUserRepositoryTest {

  private static final User USER_A = new User("User A");

  @Before
  public void init() {
      USER_A.setId("123");
  }

  @Test
  public void addFakeMentorsTest() {
    NonPersistentUserRepository testDataRepo = new NonPersistentUserRepository();
    testDataRepo.addFakeMentors();
    int expected = 3;

    Assert.assertEquals(expected, testDataRepo.getAllUsers().size());
  }

  @Test
  public void addUserTest() {
    NonPersistentUserRepository emptyRepo = new NonPersistentUserRepository();
    emptyRepo.addUser(USER_A);
    int expected = 1;

    Assert.assertEquals(expected, emptyRepo.getAllUsers().size());
  }

  @Test
  public void removeUserThatExists() {
    NonPersistentUserRepository emptyRepo = new NonPersistentUserRepository();
    emptyRepo.addUser(USER_A);
    try {
      emptyRepo.removeUser(USER_A);
      String expected = "";
      Assert.assertEquals(expected, emptyRepo.toString());
    } catch (Exception e) {
      Assert.fail("Exception should not be thrown in removeUserThatExists");
    }
  }

  @Test
  public void removeUserThatDoesNotExist() {
    NonPersistentUserRepository emptyRepo = new NonPersistentUserRepository();
    try {
      emptyRepo.removeUser(USER_A);
      Assert.fail("Exception should be thrown in removeUserThatDoesNotExist()");
    } catch (Exception e) {
      // don't need to do anything here because test should catch exception
    }
  }

  @Test
  public void getAllUsersTest() {
      User userA = new User("John");
      userA.setId("1");
      User userB = new User("Bob");
      userB.setId("2");
      User userC = new User("Haley");
      userC.setId("3");

      NonPersistentUserRepository myRepo = new NonPersistentUserRepository();
      myRepo.addUser(userA);
      myRepo.addUser(userB);
      myRepo.addUser(userC);

      Collection<User> expected = new ArrayList<User>();
      expected.add(userA);
      expected.add(userB);
      expected.add(userC);

      Assert.assertEquals(expected, myRepo.getAllUsers());
  }

  @Test
  public void addSameUserMultipleTimes() {
      User userA = new User("John");
      userA.setId("55");

      NonPersistentUserRepository myRepo = new NonPersistentUserRepository();
      myRepo.addUser(userA);
      myRepo.addUser(userA);
      myRepo.addUser(userA);

      Collection<User> allUsers = myRepo.getAllUsers();

      //myRepo should only have 1 user stored
      Assert.assertEquals(1, allUsers.size());
  }

  @Test
  public void multipleAddAndRemove() {
      User userA = new User("John");
      userA.setId("123");
      User userB = new User("Bob");
      userB.setId("345");

      NonPersistentUserRepository myRepo = new NonPersistentUserRepository();
      myRepo.addUser(userA);
      myRepo.addUser(userB);

      Collection<User> allUsers = myRepo.getAllUsers();

      //myRepo should have 2 users stored
      Assert.assertEquals(2, allUsers.size());

      try {
        myRepo.removeUser(userB);
      } catch (Exception e) {
          Assert.fail("Can't remove user that does not exist");
      }
      allUsers = myRepo.getAllUsers();
      //myRepo should now only have 1 user stored
      Assert.assertEquals(1, allUsers.size());

      myRepo.addUser(userB);
      allUsers = myRepo.getAllUsers();
      //myRepo should have 2 users stored again
      Assert.assertEquals(2, allUsers.size());
  }

}
