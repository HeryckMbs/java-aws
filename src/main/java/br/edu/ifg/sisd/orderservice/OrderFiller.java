package br.edu.ifg.sisd.orderservice;

import java.util.List;
import org.json.JSONObject;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.Message;

public class OrderFiller {
	public static void main(String[] args) {
		String queueUrl = "https://sqs.us-east-1.amazonaws.com/397450558265/order-queue";
		BasicSessionCredentials awsCreds = new BasicSessionCredentials("ASIAVZCPGHM4VFEJR3NT",
				"upn/YKpBMr531JKwzZQwx874sj+6Aq4yuO8+Dd1E",
				"FwoGZXIvYXdzEJj//////////wEaDP8h7v6Hs18cqwJ0SyLOASF7LWtw/86LDc0rk13lFyakPBAF3IF+gNEQS15C3nOck3gPyope1hex1wUPd2nrDJq3+qRTEq0DNWBeP6NDym1La//c/FAM1sQSoveMjwB0Dnr2PwPcjTnaUx4JiF4oagfu/M7JBwLDceH+O/FfEP5S7IM9pgb3rvohX7vI4LtDCj46UsFvzsWbICJkx2Vm/zdqJxlEKvnCK/Wpku0LqEp5GdWMEtN4vG2Jx4C4lf3HJ4TxVMgq/Bv1fxmb43imFzfp/jXulUMxa8MngUMaKPyT06QGMi0EBHjngl7pjvehKwoTExzO15WmvoLJYnBLO8qd9CNcciqk+MjaCdaJ0kjnd4U=");
		AmazonSQS sqs = AmazonSQSClient.builder().withRegion("us-east-1")
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
		while (true) {
			List<Message> messages = sqs.receiveMessage(queueUrl).getMessages();
			for (Message m : messages) {
				sqs.deleteMessage(queueUrl, m.getReceiptHandle());
				System.out.println("Recebeu mensagem ID " + m.getMessageId());
				String body = m.getBody();
					JSONObject json = new JSONObject(body);
				Object num = json.get("num");
				System.out.println("Preenchendo pedido # " + num);
			}
		}
	}
}