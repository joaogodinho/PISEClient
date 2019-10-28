package org.example.learnlib;

import de.learnlib.api.oracle.MembershipOracle;
import de.learnlib.api.query.Query;

import java.util.Collection;
import java.util.List;

public class ProtocolInferenceMembershipOracle implements MembershipOracle.DFAMembershipOracle<MessageTypeSymbol> {

    private final InferenceClient client;
    private NewSymbolFoundListener listener;

    ProtocolInferenceMembershipOracle(InferenceClient client) {
        this.client = client;
    }

    @Override
    public void processQueries(Collection<? extends Query<MessageTypeSymbol, Boolean>> collection) {
        for (Query<MessageTypeSymbol, Boolean> query : collection) {
            if (client.sendMembershipQuery(query)) {
                if (this.listener != null) {
                    listener.onMembershipTrue(query);
                }
            }

        }
    }

    void setListener(NewSymbolFoundListener listener) {
        this.listener = listener;
    }

    public interface NewSymbolFoundListener {
        void onMembershipTrue(Query<MessageTypeSymbol, Boolean> query);
    }
}