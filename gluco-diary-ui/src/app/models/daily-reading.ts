import { GlucoseReading } from './glucose-reading';

export class DailyReading {
    public date: string;
    public morning: GlucoseReading;
    public afternoon: GlucoseReading;
    public night: GlucoseReading;
}
