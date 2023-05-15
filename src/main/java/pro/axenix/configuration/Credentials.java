package pro.axenix.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.NoSuchElementException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {
   private List<User> ui;
   public User getUserByRole(String role){
      return ui.stream()
              .filter(user -> user.getRole().equals(role))
              .findFirst()
              .orElseThrow(() -> new NoSuchElementException("Пользователь не найден"));
   }
}
