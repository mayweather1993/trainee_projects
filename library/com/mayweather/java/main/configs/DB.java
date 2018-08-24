package configs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DB {
    private  String driverName;
    private  String connectionString;
    private  String login;
    private  String password;
}
