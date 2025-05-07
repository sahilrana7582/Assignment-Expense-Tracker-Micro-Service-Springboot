import com.example.user_service.dto.requestDto.AccountRequestDto;
import com.example.user_service.dto.responseDto.AccountResponseDto;
import com.example.user_service.enums.AccountType;
import com.example.user_service.model.Account;
import com.example.user_service.repository.AccountRepository;
import com.example.user_service.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;



import java.math.BigDecimal;


@ExtendWith(MockitoExtension.class)
public class AccountApplicationTest {


    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private AccountRequestDto accountRequestDto;

    @Mock
    private AccountResponseDto responseDto;

    @Mock
    private Account account;

    @Test
    public void testCreateAccount() {
        // Create a mock account to simulate a successful account creation
        Account mockAccount = new Account();
        mockAccount.setAccountId("testAccountId");
        mockAccount.setUserId("testUserId");
        mockAccount.setName("testName");
        mockAccount.setType(AccountType.BANK);
        mockAccount.setBalance(BigDecimal.valueOf(1000));
        mockAccount.setCurrency("USD");

        AccountRequestDto accountRequestDto = new AccountRequestDto();
        accountRequestDto.setUserId("testUserId");
        accountRequestDto.setName("testName");
        accountRequestDto.setType(AccountType.BANK);
        accountRequestDto.setBalance(BigDecimal.valueOf(1000));
        accountRequestDto.setCurrency("USD");
        accountRequestDto.setIsActive(true);

        AccountResponseDto responseDto = new AccountResponseDto();
        responseDto.setAccountId("testAccountId");
        responseDto.setUserId("testUserId");
        responseDto.setName("testName");
        responseDto.setType(AccountType.BANK);
        responseDto.setBalance(BigDecimal.valueOf(1000));
        responseDto.setCurrency("USD");
        responseDto.setActive(true);

        when(accountService.createAccount(accountRequestDto)).thenReturn(responseDto);

        AccountResponseDto created = accountService.createAccount(accountRequestDto);

        // Verify that the accountRepository.save method was called with the correct account
        assertNotNull(created);
        assertEquals(responseDto, created);
    }


}
