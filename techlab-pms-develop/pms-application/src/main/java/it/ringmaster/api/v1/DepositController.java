package it.ringmaster.api.v1;

import it.ringmaster.pms.api.DepositDto;
import it.ringmaster.pms.api.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pms/deposit")
@AllArgsConstructor
public class DepositController {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public DepositDto create(@RequestBody DepositDto depositDto) {
        return null;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DepositDto> getAllDeposits() {
        return null;
    }

    @GetMapping(value = "{deposit-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DepositDto getDeposit(@PathVariable("deposit-id") Integer depositId) {
        return null;
    }

    @GetMapping(value = "{user-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getUsers(@PathVariable("user-id") Integer userId) {
        return null;
    }
}
