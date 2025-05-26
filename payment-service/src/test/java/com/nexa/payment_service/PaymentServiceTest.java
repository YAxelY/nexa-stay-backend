// @SpringBootTest
// public class PaymentServiceTest {

//     @Mock
//     private StripeService stripeService;

//     @InjectMocks
//     private PaymentService paymentService;

//     @Test
//     void processPayment_ShouldReturnSuccess() {
//         // Given
//         PaymentRequest request = new PaymentRequest(100.0, "USD");
        
//         // When
//         when(stripeService.createPaymentIntent(any())).thenReturn(new PaymentIntent());
        
//         // Then
//         assertDoesNotThrow(() -> paymentService.processPayment(request));
//     }
// }