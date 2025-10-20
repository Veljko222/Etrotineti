package com.etrotinetbackend.backend.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import com.etrotinetbackend.backend.model.Stanje;
import com.etrotinetbackend.backend.repository.IznajmljivanjeRep;
import com.etrotinetbackend.backend.repository.StanjeRep;
import com.etrotinetbackend.backend.model.StanjeId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrotinetDostupnostServis {

    @Autowired
    private IznajmljivanjeRep iznajmljivanjeRep;

    @Autowired
    private StanjeRep stanjeRep;

    /**
     * Vrati broj dostupnih jedinica po trotinetu za dati grad, datum i vremenski period.
     */
    public Map<Long, Boolean> dostupnostPoTrotinetu(String grad, LocalDate datum, LocalTime pocetak, LocalTime kraj, Long userId) {

        Map<Long, Boolean> dostupnost = new HashMap<>();

        for (long idTrotinet = 1; idTrotinet <= 3; idTrotinet++) {

            // 1. Prebroj iznajmljivanja za dati trotinet u tom vremenskom periodu
            long rezervisana = iznajmljivanjeRep.findByGradNazivAndTrotinetIdTrotinetAndDatumAndPocetakLessThanAndKrajGreaterThan(
                    grad, idTrotinet, datum, kraj, pocetak
            ).size();

            // 2. Dohvati stanje iz tabele Stanje
            Stanje s = stanjeRep.findById(new StanjeId(idTrotinet, grad))
                                .orElse(null);

            int ukupno = s != null ? s.getStanje() : 0;

            // 3. Dostupno ako ima više jedinica od rezervisanih
            dostupnost.put(idTrotinet, rezervisana < ukupno);
        }

        return dostupnost;
    }
}
