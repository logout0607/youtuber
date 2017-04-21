/**
 * Created by moojae on 2017. 4. 8..
 */
import ColorSet from '../themes/ColorSet';

const color = [ColorSet.Red.C400, ColorSet.Red.C500, ColorSet.Red.C600, ColorSet.Red.C700, ColorSet.Red.C800, ColorSet.Red.C900, ColorSet.Red.CA200, ColorSet.Red.CA400, ColorSet.Red.CA700,
	ColorSet.Pink.C300, ColorSet.Pink.C400, ColorSet.Pink.C500, ColorSet.Pink.C600, ColorSet.Pink.C700, ColorSet.Pink.C800, ColorSet.Pink.C900, ColorSet.Pink.CA200, ColorSet.Pink.CA400, ColorSet.Pink.CA700,
	ColorSet.Purple.C300, ColorSet.Purple.C400, ColorSet.Purple.C500, ColorSet.Purple.C600, ColorSet.Purple.C700, ColorSet.Purple.C800, ColorSet.Purple.C900, ColorSet.Purple.CA200, ColorSet.Purple.CA400, ColorSet.Purple.CA700,
	ColorSet.DeepPurple.C300, ColorSet.DeepPurple.C400, ColorSet.DeepPurple.C500, ColorSet.DeepPurple.C600, ColorSet.DeepPurple.C700, ColorSet.DeepPurple.C800, ColorSet.DeepPurple.C900, ColorSet.DeepPurple.CA200, ColorSet.DeepPurple.CA400, ColorSet.DeepPurple.CA700,
	ColorSet.Indigo.C300, ColorSet.Indigo.C400, ColorSet.Indigo.C500, ColorSet.Indigo.C600, ColorSet.Indigo.C700, ColorSet.Indigo.C800, ColorSet.Indigo.C900, ColorSet.Indigo.CA200, ColorSet.Indigo.CA400, ColorSet.Indigo.CA700,
	ColorSet.Blue.C500, ColorSet.Blue.C600, ColorSet.Blue.C700, ColorSet.Blue.C800, ColorSet.Blue.C900, ColorSet.Blue.CA200, ColorSet.Blue.CA400, ColorSet.Blue.CA700,
	ColorSet.LightBlue.C600,  ColorSet.LightBlue.C700,  ColorSet.LightBlue.C800,  ColorSet.LightBlue.C900,  ColorSet.LightBlue.CA700,
	ColorSet.Cyan.C700, ColorSet.Cyan.C800, ColorSet.Cyan.C900,
	ColorSet.Teal.C500, ColorSet.Teal.C600, ColorSet.Teal.C700, ColorSet.Teal.C800, ColorSet.Teal.C900,
	ColorSet.Green.C600, ColorSet.Green.C700, ColorSet.Green.C800, ColorSet.Green.C900,
	ColorSet.LightGreen.C700, ColorSet.LightGreen.C800, ColorSet.LightGreen.C900,
	ColorSet.Lime.C900,
	ColorSet.Orange.C800, ColorSet.Orange.C900,
	ColorSet.DeepOrange.C500, ColorSet.DeepOrange.C600, ColorSet.DeepOrange.C700, ColorSet.DeepOrange.C800, ColorSet.DeepOrange.C900, ColorSet.DeepOrange.CA400, ColorSet.DeepOrange.CA700,
	ColorSet.Brown.C300, ColorSet.Brown.C400, ColorSet.Brown.C500, ColorSet.Brown.C600, ColorSet.Brown.C700, ColorSet.Brown.C800, ColorSet.Brown.C900,
	ColorSet.Grey.C600, ColorSet.Grey.C700, ColorSet.Grey.C800, ColorSet.Grey.C900,
	ColorSet.BlueGrey.C400, ColorSet.BlueGrey.C500, ColorSet.BlueGrey.C600, ColorSet.BlueGrey.C700, ColorSet.BlueGrey.C800, ColorSet.BlueGrey.C900,
	ColorSet.Black.Black
];

export function getBackgroundColor() {
	return color[Math.floor(Math.random() * color.length)];
}