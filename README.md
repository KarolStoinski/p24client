# Nieoficjalny klient przelewy24 dla Javy

Klient powstał w oparciu o dokumentację ze strony https://docs.przelewy24.pl/Płatności_internetowe

Wykorzystuje komunikację przy pomocy HTTP POST.

Napisany w czystej Javie 8, beż żadnych dodatkowych bibliotek.

## Przykłady użycia

### Zestawienie połączenia z przelewy24

Żeby zestawić połączenie podajemy wartości p24_merchant_id, p24_pos_id oraz kod CRC.

p24_merchant_id i p24_pos_id z reguły są takie same i odpowiadają identyfikatorowi z panelu przelewy24


```
P24Connection connection = new P24Connection(1234, 1234, "0affdae3cc2ee57");
```

Aby zestawić połączenie z sandboxem należy dodatkowo ustawić flagę

```
P24Connection connection = new P24Connection(1234, 1234, "0affdae3cc2ee57");
connection.setSandboxMode(true);
```

### Test połączenia do przelewy24

```
TestRequest request = connection.testRequest();
TestResponse response = request.doRequest();
```

W przypadku powodzenia w obiekcie response flaga error ustawiona będzie na false.

W przypadku wystąpienia problemu flaga będzie równa true, a w messageDescription dostępny będzie konkretny opis błędu.

### Rejestracja transakcji

Poniższy przykład ilustruje zarejestrowanie transakcji z minimalną liczbą wymaganych pól.

Poza nimi dostępne są wszystkie pola wymienione w tabeli "Parametry POST wywołania" ze strony https://docs.przelewy24.pl/Płatności_internetowe#2._Obs.C5.82uga_transakcji

```
RegisterRequest request = connection.registerRequest();

request.sessionId("ABC123")
        .amount(1000)
        .currency(Currency.PLN)
        .description("Testowa transakcja")
        .email("test@mail.pl")
        .country("PL")
        .urlReturn("http://localhost:8080/p24return");

RegisterResponse response = request.doRequest();

String paymentUrl = response.getPaymentUrl();
```

Metoda getPaymentUrl pozwala pobrać adres na który powinien zostać przekierowany płatnik.

### Weryfikacja transakcji

```
VerifyRequest request = connection.verifyRequest();

request.sessionId("ABC123")
        .amount(1000)
        .currency(Currency.PLN)
        .orderId(987654321);

VerifyResponse response = request.doRequest();
```

## Licencja

Projekt udostępniony na zasadach licencji MIT - sprawdź [LICENSE.md](LICENSE.md)