package scanner

type DummyScanner struct{}

type GreenValue struct {
	carbonEmission float64
}

type Scan interface {
	scan() GreenValue
}

func (c *DummyScanner) scan() GreenValue {
	r := GreenValue(1.0)
	return
}
