package services;

import models.units.FinancialUnit;
import services.components.FileType;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractService {
    protected static final FileType fileType = FileType.JSON;

    protected List<FinancialUnit> operation1(List<FinancialUnit> list){//в порядке убывания максимальной разницы курса за год
        return list.stream().sorted(new Comparator<FinancialUnit>() {
            @Override
            public int compare(FinancialUnit o1, FinancialUnit o2) {
                double value1 = (o1.price.high52week - o1.price.low52week )/ o1.price.low52week;
                double value2 = (o2.price.high52week - o2.price.low52week )/ o2.price.low52week;
                return Double.compare(value2, value1);
            }
        }).collect(Collectors.toList());
    }

    protected List<FinancialUnit> operation2(List<FinancialUnit> list){//в порядке убывания цены за еденицу
        return list.stream().sorted(new Comparator<FinancialUnit>() {
            @Override
            public int compare(FinancialUnit o1, FinancialUnit o2) {
                return Double.compare(o2.price.current, o1.price.current);
            }
        }).collect(Collectors.toList());
    }
}
