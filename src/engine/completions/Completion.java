package engine.completions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Completion {
    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;
    @JsonIgnore
    private String userName;
    @JsonProperty(value = "id")
    private long quizId;
    @CreationTimestamp
    private LocalDateTime completedAt;
}
