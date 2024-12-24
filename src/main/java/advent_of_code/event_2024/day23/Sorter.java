package advent_of_code.event_2024.day23;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Sorter {

    public static void main(String[] args) {
        //390
        String s = "aa, lv, bk, jk, gh, pr, de, be, mo, no, fm, ab, xz, uv, ff, wy, kq, kr, ls, fn, sz, dn, ly, mz, js, jj, tt, ii, qw, rr, dd, wx, ik, lo, rw, lu, eo, fk, cf, hp, ze, am, ve, iy, pa, fy, gz, hr, df, bd, gi, ko, hl, yb, xb, wc, cq, nn, ij, tx, zc, jt, qq, cd, pu, ag, kl, vv, oo, ru, nr, bg, rz, xy, sv, fj, hn, lr, bi, il, gg, xx, cc, ku, my, sa, pp, jo, bc, st, eg, tv, ad, pt, nt, qz, jz, rb, in, ss, lp, ya, nv, al, nq, ks, nw, ox, hw, zi, oz, fr, lt, ww, su, ju, an, gm, op, bf, ot, cj, dk, bj, ow, fo, ap, nc, si, me, ep, cv, xl, qa, vd, cr, ct, kz, mn, jm, tw, ej, tz, bl, gt, xh, hy, kb, bm, zb, fq, sx, dh, kp, af, at, kt, ip, yc, cn, xd, yi, qb, du, qc, re, pd, mq, ah, jv, ix, ue, yh, ob, ew, go, iq, en, ua, uc, ms, py, fp, xe, vj, qg, ar, bn, vi, gw, zl, fz, wq, xi, av, bv, cp, hk, im, hm, qx, el, qy, ui, ne, zj, tb, yk, tg, rf, dj, px, va, co, ds, sb, sd, bu, nz, ht, iu, gu, sy, yd, fh, gj, fl, mv, yj, bs, bt, na, zo, or, dg, ty, rx, ho, zz, kk, io, wb, nu, ef, ry, wz, vy, ei, is, vz, uz, mt, ai, cl, mx, tc, bx, xf, ns, aj, vc, es, xg, vf, aw, wp, xq, se, km, hi, jl, oq, ae, jn, ll, lq, gn, yf, tf, pc, sl, az, tn, lb, gp, it, gv, od, md, zs, cw, fw, nb, th, wl, xr, sp, wm, dl, ye, ji, kv, hu, dq, zg, ra, gx, jr, hq, pz, hs, td, zn, uf, fu, dp, ta, sh, cy, fs, wa, vb, ky, dw, qf, uu, ov, dt, sf, qe, xm, xn, rh, by, yt, vt, hf, ut, xk, zf, lg, lx, wh, ex, dx, mb, gy, xj, os, gs, rd, ym, bw, ax, lf, jf, rj, kd, ng, ur, xu, er, ey, oe, sn, fb, li, vs, zt, ie, ha, ub, et, lc, bz, fc, da, gc, ri, nj, mi, og, oj, aq, dv, cu, xo, zu, so, tq";
        String[] split = s.split(",\\s+");
        String res = Arrays.stream(split)
                .sorted()
                .collect(Collectors.joining(","));
        System.out.println(res);
    }

}
